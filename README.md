# Proyecto-Móviles
MapRealTime es una aplicación desarrollada en Flutter que ofrece funcionalidades avanzadas para la gestión y visualización de terrenos, así como para el seguimiento en tiempo real de topógrafos. A continuación se describen las principales características y funcionalidades de la aplicación.

## Integrantes
* Alejandro Moreira
* Ismael Novillo
* Jared Valenzuela
* Joseph Yépez

### Login para Validación de Usuarios 
La aplicación permite a los usuarios iniciar sesión utilizando Firebase Authentication. El proceso de inicio de sesión incluye validación de credenciales y redirección según el rol del usuario. Los roles disponibles son 'Usuario' y 'Administrador', y se manejan las redirecciones apropiadas después del inicio de sesión.

### Sistema de Administración Web/Móvil 
El sistema de administración permite gestionar usuarios y administradores tanto desde la web como desde la aplicación móvil. Las funcionalidades incluyen:

* Agregar nuevos usuarios.
* Eliminar usuarios.
* Desactivar cuentas de usuarios.

### Visualización de Terrenos y sus Características 
La aplicación permite visualizar terrenos con detalles clave, incluyendo:

* Ubicación del terreno en el mapa.
* Polígono definido que representa el área del terreno.
* Área del terreno calculada automáticamente.

### Geolocalización en Tiempo Real 
La geolocalización en tiempo real se implementa utilizando la API de Google Maps. La aplicación actualiza automáticamente la posición de los topógrafos en el mapa y genera enlaces de geolocalización conforme cambian sus posiciones. Esto proporciona una visualización precisa y actualizada de la ubicación de los topógrafos.

### Cálculo del Área del Polígono
La aplicación incluye una funcionalidad para calcular el área de polígonos que representan terrenos. El cálculo se realiza de manera automática utilizando algoritmos de geometría y se muestra el área calculada en la interfaz de usuario.
