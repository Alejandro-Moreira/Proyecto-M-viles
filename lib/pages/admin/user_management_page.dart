import 'package:flutter/material.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:logger/logger.dart';

final logger = Logger();

class UserManagementPage extends StatefulWidget {
  const UserManagementPage({super.key});

  @override
  _UserManagementPageState createState() => _UserManagementPageState();
}

class _UserManagementPageState extends State<UserManagementPage> {
  @override
  void initState() {
    super.initState();
    _checkAuthentication();
  }

  Future<void> _checkAuthentication() async {
    User? user = FirebaseAuth.instance.currentUser;
    if (user == null) {
      Navigator.of(context).pushReplacementNamed('/login'); // Redirige al login si no está autenticado
    }
  }

  Future<void> _addUser(String nombre, String apellido, String email, String password, BuildContext context) async {
    try {
      // Crear el usuario en Firebase Auth
      UserCredential userCredential = await FirebaseAuth.instance.createUserWithEmailAndPassword(
        email: email,
        password: password,
      );

      // Agregar el usuario a Firestore en la colección 'users'
      await FirebaseFirestore.instance.collection('users').doc(userCredential.user!.uid).set({
        'nombre': nombre,
        'apellido': apellido,
        'email': email,
        'role': 'Usuario', // Rol predeterminado
        'active': true,
      });

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Usuario agregado exitosamente.'),
        ),
      );
    } catch (e) {
      logger.i('Error al agregar el usuario: $e');
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Error al agregar el usuario.'),
        ),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Administrar Usuarios'),
        actions: [
          IconButton(
            icon: const Icon(Icons.add),
            onPressed: () {
              _showAddUserDialog(context);
            },
          ),
        ],
      ),
      body: _buildUserList(),
    );
  }

  Widget _buildUserList() {
    return StreamBuilder<QuerySnapshot>(
      stream: FirebaseFirestore.instance.collection('users').snapshots(),
      builder: (context, snapshot) {
        if (!snapshot.hasData) {
          return const Center(child: CircularProgressIndicator());
        }

        var users = snapshot.data!.docs;

        return ListView.builder(
          itemCount: users.length,
          itemBuilder: (context, index) {
            var user = users[index].data() as Map<String, dynamic>;
            String userId = users[index].id;
            bool isActive = user['active'];

            return ListTile(
              title: Text('${user['nombre']} ${user['apellido']}'),
              subtitle: Text(user['email']),
              trailing: Row(
                mainAxisSize: MainAxisSize.min,
                children: [
                  IconButton(
                    icon: Icon(isActive ? Icons.remove_circle : Icons.add_circle, color: isActive ? Colors.red : Colors.green),
                    onPressed: () async {
                      await _toggleUserStatus(userId, isActive);
                    },
                  ),
                  IconButton(
                    icon: const Icon(Icons.delete, color: Colors.red),
                    onPressed: () async {
                      bool? confirm = await _confirmDeletion(context);
                      if (confirm == true) {
                        await _deleteUser(userId, context);
                        // Forzar actualización
                        setState(() {});
                      }
                    },
                  ),
                ],
              ),
            );
          },
        );
      },
    );
  }

  Future<void> _toggleUserStatus(String userId, bool isActive) async {
    try {
      await FirebaseFirestore.instance.collection('users').doc(userId).update({'active': !isActive});
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Estado del usuario actualizado.'),
        ),
      );
    } catch (e) {
      logger.i('Error al actualizar el estado del usuario: $e');
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Error al actualizar el estado del usuario.'),
        ),
      );
    }
  }

  Future<void> _deleteUser(String userId, BuildContext context) async {
    try {
      // Elimina el usuario de Firestore usando el UID del documento
      await FirebaseFirestore.instance.collection('users').doc(userId).delete();
      logger.i('Documento eliminado de Firestore');

      // Eliminar el usuario de Firebase Auth
      User? user = await _getUserById(userId);
      if (user != null) {
        await user.delete();
        logger.i('Usuario eliminado de Firebase Auth');
      }

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Usuario eliminado exitosamente.'),
        ),
      );
    } catch (e) {
      logger.i('Error al eliminar el usuario: $e');
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Error al eliminar el usuario.'),
        ),
      );
    }
  }

  Future<User?> _getUserById(String uid) async {
    try {
      User? user = FirebaseAuth.instance.currentUser;
      if (user != null && user.uid == uid) {
        return user;
      }
      return null;
    } catch (e) {
      logger.i('Error al obtener el usuario por UID: $e');
      return null;
    }
  }

  Future<bool?> _confirmDeletion(BuildContext context) async {
    return showDialog<bool>(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text('Confirmar Eliminación'),
          content: const Text('¿Estás seguro de que deseas eliminar este usuario?'),
          actions: <Widget>[
            TextButton(
              child: const Text('Cancelar'),
              onPressed: () => Navigator.of(context).pop(false),
            ),
            TextButton(
              child: const Text('Eliminar'),
              onPressed: () => Navigator.of(context).pop(true),
            ),
          ],
        );
      },
    );
  }

  void _showAddUserDialog(BuildContext context) {
    final TextEditingController _emailController = TextEditingController();
    final TextEditingController _passwordController = TextEditingController();
    final TextEditingController _nombreController = TextEditingController();
    final TextEditingController _apellidoController = TextEditingController();

    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text('Agregar Usuario'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextField(
                controller: _nombreController,
                decoration: const InputDecoration(labelText: 'Nombre'),
              ),
              TextField(
                controller: _apellidoController,
                decoration: const InputDecoration(labelText: 'Apellido'),
              ),
              TextField(
                controller: _emailController,
                decoration: const InputDecoration(labelText: 'Correo'),
              ),
              TextField(
                controller: _passwordController,
                decoration: const InputDecoration(labelText: 'Contraseña'),
                obscureText: true,
              ),
            ],
          ),
          actions: <Widget>[
            TextButton(
              child: const Text('Cancelar'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
            TextButton(
              child: const Text('Agregar'),
              onPressed: () async {
                await _addUser(
                  _nombreController.text,
                  _apellidoController.text,
                  _emailController.text,
                  _passwordController.text,
                  context,
                );
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }
}
