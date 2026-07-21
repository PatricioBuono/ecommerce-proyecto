## Historias de Usuario: Módulo 1 (IAM)

### 1. **Registro de Nuevo Cliente**

   **Historia de Usuario:** Como visitante de la tienda, quiero crear una cuenta utilizando mis datos personales y email, para poder acceder a las funcionalidades de compra del sistema.

   **Criterios de Aceptación:**
   - Validación de Email: El sistema debe verificar que el email ingresado no exista ya en la base de datos. Si existe, debe mostrar un mensaje: "Este correo ya está registrado".
   - Formato de Email: El sistema debe validar que el email ingresado tenga una estructura de correo electrónico válida (ej: usuario@dominio.com).
   - Política de contraseñas: El formulario debe validar que la contraseña tenga como mínimo 8 caracteres, al menos una letra mayúscula, un número y un carácter especial. Si no cumple, el botón de registro debe estar deshabilitado o mostrar un error de formato.
   - Asignación de Rol: Al crearse exitosamente, el sistema debe asignarle por defecto el rol de "CLIENTE" a este nuevo usuario, aislando la lógica en el backend por seguridad. 
   - Nota técnica MVP: La encriptación de contraseñas con BCrypt queda fuera del alcance de esta iteración inicial y será abordada en la integración global del módulo de Seguridad.

### 2. **Inicio y Persistencia de Sesión (Login)**

   **Historia de Usuario:** Como cliente registrado, quiero iniciar sesión con mi email y contraseña, para que el sistema me identifique y mantenga mi sesión abierta mientras navego.
    
   **Criterios de Aceptación:**
   - Validación de Credenciales: El sistema debe verificar que el email y la contraseña coincidan con los de la base de datos. Si fallan, mostrar: "Credenciales incorrectas".
   - Generación de Sesión (JWT): Tras un login exitoso, el backend debe generar y devolver un Token JWT válido.
   - Persistencia en el Navegador: El frontend (React) debe guardar este Token (por ejemplo, en el localStorage o en una cookie segura) para que el usuario no tenga que volver a loguearse al recargar la página.
   - Redirección: Al loguearse con éxito, el sistema debe redirigir al usuario al Catálogo Principal.

### 3. **Cierre de Sesión (Logout)** 

   **Historia de Usuario:** Como usuario autenticado, quiero poder cerrar mi sesión para proteger mi privacidad y evitar que otros accedan a mi cuenta desde este dispositivo.

   **Criterios de Aceptación:**
   - Dado que un usuario tiene una sesión activa, cuando hace clic en el botón "Cerrar sesión", entonces el sistema debe eliminar el token y el usuario del almacenamiento local (Local Storage).
   - Dado que el usuario cerró su sesión, entonces el sistema debe redirigirlo automáticamente a la pantalla de inicio de sesión.
   - Dado que el usuario cerró su sesión e intenta navegar hacia atrás, entonces no debe poder realizar acciones que requieran autenticación.

### 4. **Barra de Navegación Dinámica**

   **Historia de Usuario:** Como usuario, quiero que la barra de navegación refleje mi estado de sesión (logueado o no logueado) para saber rápidamente si estoy navegando con mi cuenta activa o como invitado.

   **Criterios de Aceptación:** 
   - Dado que un usuario visitante (no autenticado) ingresa a la aplicación, entonces la barra de navegación debe mostrar las opciones "Iniciar sesión" y "Crear cuenta".
   - Dado que un usuario inicia sesión exitosamente, entonces la barra de navegación debe actualizarse en tiempo real, ocultando los botones de acceso y mostrando el mensaje "¡Hola, [Nombre del Usuario]!".
   - Dado que un usuario autenticado está navegando, entonces la barra de navegación debe mantener visible el botón de "Cerrar sesión" en todo momento.

