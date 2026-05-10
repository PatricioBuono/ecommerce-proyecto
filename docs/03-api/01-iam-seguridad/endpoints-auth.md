## ENDPOINTS - MODULO Nº1 - 

### 1. **Registro de Cliente**

**Método:** POST

**Ruta:**/api/auth/registro

**Descripción:** Crea una nueva cuenta de usuario y le asigna el rol "CLIENTE" por defecto.


***Request Body:*** (Formato JSON)
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan.perez@gmail.com",
  "password": "12345"
}
```
    
***Caso de Éxito (HTTP 201 - Created):***
```json
{
  "mensaje": "Usuario registrado con éxito",
  "usuario": {
    "id": 1,
    "nombre": "Juan",
    "apellido": "Pérez",
    "email": "juan.perez@email.com"
  }
}
```

***Caso de Error 1: Email duplicado (HTTP 409 - Conflict):***
```json
{
  "error": "Conflict",
  "mensaje": "Este correo ya está registrado en el sistema."
}
```

***Caso de Error 2: Contraseña débil (HTTP 400 - Bad Request):***
```json
{
  "error": "Bad Request",
  "mensaje": "La contraseña debe tener mínimo 8 caracteres, una mayúscula, un número y un carácter especial."
}
```


### 2. **Inicio de Sesión**

**Método:** POST
    
**Ruta:**/api/auth/login
    
**Descripción:** Valida las credenciales del usuario en la base de datos. Si son correctas, devuelve un Token JWT para mantener la sesión abierta de forma segura.

***Request Body:*** (Formato JSON)
```json
{
  "email": "juan.perez@gmail.com",
  "password": "12345"
}
```

***Caso de Éxito (HTTP 200 - OK):***
```json
{
  "mensaje": "Login exitoso",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMj...", 
  "usuario": {
    "id": 1,
    "nombre": "Juan",
    "email": "juan.perez@gmail.com",
    "rol": "CLIENTE"
  }
}
```

***Caso de Error 1: Credenciales inválidas (HTTP 401 - Unauthorized):***
```json
{
  "error": "Unauthorized",
  "mensaje": "El correo o contraseña son incorrectos."
}
```
