# **MapRealTime App**
## **Integrantes**
-MORERIRA ALEJANDRO 

-NOVILLO ISMAEL

-VALENZUELA JARED

-YEPEZ JOSHEP

 
## **Índice**

1. Descripción del Proyecto
2. Video de funcionamiento
3. Características
4. Servicios
    - AuthService
        - Métodos
    - CalculationService
        - Métodos
    - LocationService
        - Métodos
5. Instalación y Configuración
6. Uso
    - AuthService
    - CalculationService
7. Capturas de Pantalla

## **Descripción del Proyecto**

**MapRealTime** es una aplicación diseñada para proporcionar funcionalidades de mensajería y cálculo basado en la geolocalización. Utiliza Firebase para la autenticación, la gestión de mensajes y el almacenamiento en tiempo real de datos. El administrador puede realizar cálculos sobre distancias y áreas utilizando ubicaciones geográficas de acuerdo a  la ubicación de cada Minero.

## **Video de Funcionamiento**
https://youtu.be/oyrO27aspD8

## **Características**

- **Autenticación de Usuario:** Inicio de sesión y cierre de sesión con diferentes roles (Usuario o Administrador).
- **Mensajería en Tiempo Real:** Envío y recepción de ubicaciones geográficas.
- **Cálculo Geoespacial:** Cálculo de distancias entre ubicaciones y perímetros y áreas de polígonos formados por múltiples ubicaciones.
- **Actualización de Ubicación:** Actualización y gestión de ubicaciones de los usuarios.

## **Servicios**

### **AuthService**

Gestiona la autenticación de los usuarios y su redirección según su rol.

### **Métodossignin**

Inicia sesión con las credenciales proporcionadas.

```dart
Future<void> signin({
  required String email,
  required String password,
  required BuildContext context,
});
```

- **signout**
    
    Cierra la sesión del usuario actual.
    
    ```dart
    Future<void> signout({
      required BuildContext context,
    });
    ```
    

### **CalculationService**

Realiza cálculos basados en ubicaciones de usuarios, incluyendo distancias, perímetros y áreas.

### **Métodos**

- **calculate**Calcula distancia entre dos ubicaciones o perímetro y área de un polígono.
    
    ```dart
    Future<Map<String, dynamic>> calculate();
    ```
    

### **ChatService**

Maneja la gestión de ubicaciones de usuarios.

### **Métodos**

- **getMessages**
    
    Obtiene un flujo de mensajes ordenados por timestamp.
    
    ```dart
    dartCopiar código
    Stream<QuerySnapshot> getMessages();
    ```
    
- **sendMessage**
    
    Envía un mensaje a la base de datos de Firebase, con la opción de incluir una ubicación.
    
    ```dart
    Future<void> sendMessage(
      String message,
      String userId, {
      bool isLocation = false,
      GeoPoint? location,
    });
    ```
    
- **updateUserLocation**
    
    Actualiza la ubicación de un usuario en la base de datos.
    
    ```dart
    Future<void> updateUserLocation(String userId, GeoPoint location);
    ```
    

## **Instalación y Configuración**

1. **Clonar el Repositorio:**
    
    ```bash
    git clone https://github.com/Alejandro-Moreira/Proyecto-M-viles.git
    ```
    
2. **Navegar al Directorio del Proyecto:**
    
    ```bash
    cd Proyecto-M-viles
    ```
    
3. **Instalar Dependencias:**
    
    ```bash
    flutter pub get
    ```
    
4. **Configurar Firebase:**
    - Sigue la guía de configuración de Firebase para añadir tu archivo de configuración (`google-services.json` para Android o `GoogleService-Info.plist` para iOS).
    - Asegúrate de haber configurado las reglas de seguridad adecuadas para Firestore y Authentication en la consola de Firebase.
5. **Ejecutar la Aplicación:**
    
    ```bash
    flutter run
    ```
    

## **Uso**

- **Inicio de Sesión:**
    
    ```dart
    AuthService().signin(
      email: 'example@example.com',
      password: 'your_password',
      context: context,
    );
    ```
    
- **Cierre de Sesión:**
    
    ```dart
    AuthService().signout(
      context: context,
    );
    ```
    

### **CalculationService**

- **Calcular Distancias o Áreas:**
    
    ```dart
    CalculationService().calculate().then((result) {
      if (result['type'] == 'distance') {
        print('Distance: ${result['value']} km');
      } else if (result['type'] == 'area') {
        print('Perimeter: ${result['perimeter']} km');
        print('Area: ${result['area']} km²');
      } else {
        print(result['message']);
      }
    });
    
    ```
    

### **ChatService**
    
- **Enviar Mensaje a la base de Datos de Firebase:**
    
    ```dart
    ChatService().sendMessage(
      'Hello World!',
      'user123',
      isLocation: true,
      location: GeoPoint(37.7749, -122.4194),
    );
    ```
    
- **Actualizar Ubicación del Usuario:**
    
    ```dart
    ChatService().updateUserLocation(
      'user123',
      GeoPoint(37.7749, -122.4194),
    );
    ```
    

## Capturas de pantalla
Base de datos 

 Autentificacion 
 
![image](https://github.com/user-attachments/assets/49bc1ac0-b555-4f4e-b686-4cead24047ad)

 Admin
 
![image](https://github.com/user-attachments/assets/0d2a2cd1-a110-4b85-8b96-4ffb5b048a07)

 Usuarios
 
 ![image](https://github.com/user-attachments/assets/41284480-dcce-4999-962c-1691ecf9be71)
 

Pantalla para usuarios (Topografos)

![image](https://github.com/user-attachments/assets/eb52e7ab-4410-4b82-a1bb-f62742024268)


Pantalla para administradores 

![image](https://github.com/user-attachments/assets/6d1d0c42-1132-40b0-8100-71465ae47803)

Despliegue en la web 

Despliegue en tienda de aplicaciones 

![image](https://github.com/user-attachments/assets/739525c2-4d7d-4495-8a35-935c4efb3553)

