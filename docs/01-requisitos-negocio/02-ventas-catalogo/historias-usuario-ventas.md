## Historias de Usuario: Módulo 2 (Core Business)

### 1. **Catálogo General**

**Historia de Usuario:** Como comprador, quiero ver una lista de los productos disponibles con su imagen, nombre y precio, para poder explorar la tienda y decidir qué comprar.

**Criterios de Aceptación:**
- El sistema debe mostrar los productos en la pantalla principal en formato de grilla o lista. 
- Cada tarjeta de producto debe mostrar obligatoriamente: Foto (si la tiene), Título y Precio.
- Si la base de datos no tiene productos cargados, el frontend debe mostrar un mensaje: "No hay productos disponibles por el momento".

### 2. **Detalle del Producto**

**Historia de Usuario:** Como comprador, quiero hacer clic en un producto específico del catálogo para ver su información detallada (stock, categoría y descripción), para evaluar si me sirve y decidir si agregarlo al carrito.
    
**Criterios de Aceptación:**
- Al hacer clic en la tarjeta de un producto en el catálogo, el sistema debe redirigir al usuario a la vista de "Detalle del Producto".
- Esta vista debe mostrar la información completa obtenida de la base de datos: Imagen, Título, Precio, Descripción completa, Categoría y Cantidad de Stock actual. 
- Si el stock es 0, se debe mostrar un indicador visual de "Agotado".

### 3. **Gestión del Carrito (ÉPICA - frontend)**

**Historia de Usuario:** Como comprador, quiero poder agregar productos al carrito y visualizar un panel lateral con el resumen de mi selección, para llevar un control de lo que voy a comprar y el monto total.

**Criterios de Aceptación:**
- Agregar productos: Cada tarjeta de producto debe tener un botón/selector para añadir al carrito. Si el producto ya existe en el carrito, se debe incrementar la cantidad en lugar de duplicar el ítem. 
- Validación de Stock: El sistema no debe permitir agregar una cantidad superior al stock disponible. Si se intenta, debe mostrar el mensaje de error: "Stock insuficiente". 
- Visualización (Off-canvas): Debe existir un botón de "Ver Carrito" visible en la navegación. Al hacer clic, se debe desplegar un panel lateral (off-canvas) sin cambiar de página. 
- Contenido del Carrito: El panel debe mostrar la lista de productos seleccionados, la cantidad de cada uno, el subtotal por producto y el monto total de la compra. 
- Eliminar ítems: El usuario debe tener la opción de eliminar un producto completo del carrito o reducir su cantidad a cero desde el panel lateral. 
- Si no se hace la compra en el momento, debe quedar guardado el carrito con los productos seleccionados (pero no se descuenta del stock hasta que no se compre - Local Storage).

### 4. **Confirmación de Compra (Checkout Simulado) - ÉPICA -**

**Épica:** Como comprador con artículos en el carrito, quiero confirmar mi compra para generar mi pedido, asegurar los productos y vaciar mi carrito.

**Criterios de Aceptación:**
- Botón de Acción: El panel del carrito (off-canvas) debe tener un botón de "Confirmar Compra" (solo habilitado si hay productos en el carrito y el usuario está logueado). 
- Generación de Orden: Al hacer clic, el sistema debe registrar la orden en la base de datos asociada al usuario actual, incluyendo el monto total y el estado inicial de la orden (por ejemplo: "Completada" para este MVP). 
- Historial Inmutable: Se deben guardar los detalles de la compra (qué productos, qué cantidad y el precio exacto al momento de hacer clic) para el historial del cliente. 
- Actualización de Inventario: El sistema debe descontar automáticamente del stock general las cantidades compradas de cada producto. 
- Limpieza de Sesión: Una vez que la transacción en la base de datos es exitosa, el carrito del usuario debe quedar completamente vacío. 
- Feedback Visual: Se debe mostrar un mensaje claro en el frontend (ej. un toast o alerta) indicando: "Compra realizada con éxito".

### 4.1. **API de Procesamiento de Checkout**

**Historia de Usuario:** Como sistema, necesito recibir el resumen de la compra, validar el stock y persistir la orden de forma transaccional para garantizar la integridad de los datos y el historial de precios.

**Criterios de Aceptación:**
- Endpoint: Implementar POST /api/ordenCompra.
- Seguridad y Cálculo: El sistema debe calcular el monto total consultando los precios históricos en la base de datos, ignorando cualquier precio enviado desde el cliente.
- Transaccionalidad: Si un solo producto de la lista no tiene stock suficiente, se debe cancelar toda la operación (Rollback) y devolver un error 400 Bad Request.
- Persistencia Inmutable: Se debe guardar la orden con estado "COMPLETADA" y sus detalles vinculados, congelando el precio y cantidad exacta al momento de la compra.
- Actualización de Inventario: Se debe descontar del stock general de cada producto la cantidad adquirida.
- Respuesta: Devolver HTTP 201 Created con el ID de la orden, el monto total calculado y el estado.

### 4.2. **UI de Confirmación de Compra**

**Historia de Usuario:** Como comprador, quiero enviar mi carrito al servidor y ver un mensaje de éxito para tener la confirmación de que mi pedido fue procesado.

**Criterios de Aceptación:**
- Botón de Acción: El panel lateral del carrito debe tener el botón "Confirmar Compra", habilitado solo si el carrito tiene ítems y el usuario está autenticado.
- Integración: Al hacer clic, enviar la petición POST al endpoint /api/ordenCompra enviando únicamente los IDs y cantidades.
- Limpieza (Local Storage): Si el servidor responde con éxito (201), el carrito debe vaciarse automáticamente borrando los datos de la memoria del navegador.
- Feedback Visual: Mostrar un mensaje claro al usuario (ej. toast) con el ID de su orden y redirigirlo o cerrar el panel.
- Manejo de Errores: Si el servidor devuelve un error 400 por falta de stock, mostrar la alerta correspondiente al usuario sin borrar su carrito.

### 5. **Carrito de Compras para Visitantes**

**Historia de Usuario:** Como usuario visitante (no autenticado), quiero poder agregar productos al carrito y que se guarden en mi navegador, para no perder mi selección mientras sigo navegando por el catálogo.

**Criterios de Aceptación:**
- Dado que un usuario no tiene sesión iniciada, cuando hace clic en "Agregar al carrito", entonces el producto y su cantidad deben guardarse en la memoria del navegador (Local Storage).
- Dado que un usuario visitante tiene productos en su carrito local, cuando recarga la página o cierra y vuelve a abrir la pestaña, entonces el carrito debe mantener los productos seleccionados.

### 6. **Fusión de Carrito al Autenticarse**

**Historia de Usuario:** Como usuario que preseleccionó productos siendo visitante, quiero que estos se sumen automáticamente a mi cuenta al iniciar sesión o registrarme, para poder finalizar mi compra sin tener que volver a buscarlos.

**Criterios de Aceptación:**
- Fusión en Login: Dado que el visitante tiene productos en el carrito local, cuando inicia sesión exitosamente, entonces el sistema debe enviar esos productos al backend y sumarlos a los productos que el usuario ya pudiera tener guardados en su cuenta.
- Fusión en Registro: Dado que el visitante tiene productos en el carrito local, cuando crea una cuenta nueva, entonces el sistema debe crear su carrito en la base de datos con esos productos.
- Limpieza: Dado que el backend confirmó la fusión o creación del carrito exitosamente, cuando el sistema recibe la respuesta de éxito, entonces se debe borrar el carrito temporal del local storage.
