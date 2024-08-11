# **MapRealTime App**

## **Índice**

1. [Descripción del Proyecto]
2. [Características]
3. [Servicios]
    - [AuthService]
        - [Métodos]
    - [CalculationService]
        - [Métodos]
    - [LocationService]
        - [Métodos]
4. [Instalación y Configuración]
5. [Uso]
    - [AuthService]
    - [CalculationService]
    - [ChatService]
6. [Capturas de Pantalla]
7. [Contribuciones]
8. [Licencia]

## **Descripción del Proyecto**

**MapRealTime** es una aplicación diseñada para proporcionar funcionalidades de mensajería y cálculo basado en la geolocalización. Utiliza Firebase para la autenticación, la gestión de mensajes y el almacenamiento en tiempo real de datos. El administrador puede realizar cálculos sobre distancias y áreas utilizando ubicaciones geográficas de acuerdo a  la ubicación de cada Minero.

## **Características**

- **Autenticación de Usuario:** Inicio de sesión y cierre de sesión con diferentes roles (Usuario o Administrador).
- **Mensajería en Tiempo Real:** Envío y recepción de ubicaciones geográficas.
- **Cálculo Geoespacial:** Cálculo de distancias entre dos ubicaciones y perímetros y áreas de polígonos formados por múltiples ubicaciones.
- **Actualización de Ubicación:** Actualización y gestión de ubicaciones de los usuarios.

## **Servicios**

### **AuthService**

Gestiona la autenticación de los usuarios y su redirección según su rol.

### **Métodossignin**

Inicia sesión con las credenciales proporcionadas.

```dart
dartCopiar código
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
    
- **deleteMessage**
    
    Elimina un mensaje específico del chat.
    
    ```dart
    Future<void> deleteMessage(String messageId);
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

- **Obtener Mensajes:**
    
    ```dart
    ChatService().getMessages().listen((QuerySnapshot snapshot) {
      // Manejar los mensajes recibidos
    });
    ```
    
- **Enviar Mensaje a la base de Datos de Firebase:**
    
    ```dart
    dartCopiar código
    ChatService().sendMessage(
      'Hello World!',
      'user123',
      isLocation: true,
      location: GeoPoint(37.7749, -122.4194),
    );
    ```
    
- **Eliminar Mensaje:**
    
    ```dart
    ChatService().deleteMessage('messageId123');
    ```
    
- **Actualizar Ubicación del Usuario:**
    
    ```dart
    ChatService().updateUserLocation(
      'user123',
      GeoPoint(37.7749, -122.4194),
    );
    ```
    

## Capturas de pantalla
