import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import '../pages/home/home.dart';
import '../pages/login/login.dart';
import '../pages/admin/admin.dart'; 

class AuthService {
  Future<bool> signup({
    required String email,
    required String password,
    required String role,
    required BuildContext context,
  }) async {
    try {
      UserCredential userCredential = await FirebaseAuth.instance.createUserWithEmailAndPassword(
        email: email,
        password: password,
      );

      String collectionName = role == 'Administrador' ? 'administradores' : 'users';
      String uid = userCredential.user!.uid;

      await FirebaseFirestore.instance
          .collection(collectionName)
          .doc(uid)
          .set({
        'email': email,
        'role': role,
      });

      await Future.delayed(const Duration(seconds: 1));

      // Redirige basado en el rol
      if (role == 'Administrador') {
        Navigator.pushReplacement(
          context,
          MaterialPageRoute(
            builder: (BuildContext context) => const AdminMap(),
          ),
        );
      } else {
        Navigator.pushReplacement(
          context,
          MaterialPageRoute(
            builder: (BuildContext context) => const Home(),
          ),
        );
      }

      return true; // Registro exitoso
    } on FirebaseAuthException catch (e) {
      String message = '';
      if (e.code == 'weak-password') {
        message = 'La contraseña es muy débil.';
      } else if (e.code == 'email-already-in-use') {
        message = 'Ya existe una cuenta registrada con ese correo electrónico.';
      }
      Fluttertoast.showToast(
        msg: message,
        toastLength: Toast.LENGTH_LONG,
        gravity: ToastGravity.SNACKBAR,
        backgroundColor: Colors.black54,
        textColor: Colors.white,
        fontSize: 14.0,
      );
      return false; // Error al registrar
    } catch (e) {
      Fluttertoast.showToast(
        msg: 'Error al registrar: $e',
        toastLength: Toast.LENGTH_LONG,
        gravity: ToastGravity.SNACKBAR,
        backgroundColor: Colors.black54,
        textColor: Colors.white,
        fontSize: 14.0,
      );
      return false; // Error al registrar
    }
  }

  Future<void> signin({
    required String email,
    required String password,
    required BuildContext context,
  }) async {
    try {
      UserCredential userCredential = await FirebaseAuth.instance.signInWithEmailAndPassword(
        email: email,
        password: password,
      );

      String uid = userCredential.user!.uid;
      DocumentSnapshot userDoc = await FirebaseFirestore.instance.collection('users').doc(uid).get();
      DocumentSnapshot adminDoc = await FirebaseFirestore.instance.collection('administradores').doc(uid).get();

      String role = '';
      if (adminDoc.exists) {
        role = 'Administrador';
      } else if (userDoc.exists) {
        role = 'Usuario';
      }

      await Future.delayed(const Duration(seconds: 1));

      // Redirige basado en el rol
      if (role == 'Administrador') {
        Navigator.pushReplacement(
          context,
          MaterialPageRoute(
            builder: (BuildContext context) => const AdminMap(),
          ),
        );
      } else if (role == 'Usuario') {
        Navigator.pushReplacement(
          context,
          MaterialPageRoute(
            builder: (BuildContext context) => const Home(),
          ),
        );
      } else {
        Fluttertoast.showToast(
          msg: 'Rol de usuario no encontrado',
          toastLength: Toast.LENGTH_LONG,
          gravity: ToastGravity.SNACKBAR,
          backgroundColor: Colors.black54,
          textColor: Colors.white,
          fontSize: 14.0,
        );
      }
    } on FirebaseAuthException catch (e) {
      String message = '';
      if (e.code == 'invalid-email') {
        message = 'El correo electrónico no se encuentra registrado.';
      } else if (e.code == 'wrong-password') {
        message = 'Contraseña incorrecta.';
      }
      Fluttertoast.showToast(
        msg: message,
        toastLength: Toast.LENGTH_LONG,
        gravity: ToastGravity.SNACKBAR,
        backgroundColor: Colors.black54,
        textColor: Colors.white,
        fontSize: 14.0,
      );
    } catch (e) {
      Fluttertoast.showToast(
        msg: 'Error al iniciar sesión: $e',
        toastLength: Toast.LENGTH_LONG,
        gravity: ToastGravity.SNACKBAR,
        backgroundColor: Colors.black54,
        textColor: Colors.white,
        fontSize: 14.0,
      );
    }
  }

  Future<void> signout({
    required BuildContext context,
  }) async {
    await FirebaseAuth.instance.signOut();
    await Future.delayed(const Duration(seconds: 1));
    Navigator.pushReplacement(
      context,
      MaterialPageRoute(
        builder: (BuildContext context) => Login(),
      ),
    );
  }
}
