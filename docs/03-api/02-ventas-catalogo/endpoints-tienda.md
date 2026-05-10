## ENDPOINTS - MODULO Nº2 -

### 1. **Obtener Catálogo General**

**Método:** GET

**Ruta:**/api/productos

**Descripción:** Devuelve la lista completa de productos activos para ser mostrados en la pantalla principal del e-commerce.

***Request Body:*** Ninguno

***Caso de Éxito (HTTP 200 - OK):*** El servidor devuelve un array con los productos.
```json
[
  {
    "id": 1,
    "titulo": "Teclado Mecánico RGB",
    "precio": 45000.00,
    "imagenUrl": "[https://tuservidor.com/imagenes/teclado.jpg](https://tuservidor.com/imagenes/teclado.jpg)",
    "stock": 15
  },
  {
    "id": 2,
    "titulo": "Mouse Gamer Inalámbrico",
    "precio": 28000.00,
    "imagenUrl": "[https://tuservidor.com/imagenes/mouse.jpg](https://tuservidor.com/imagenes/mouse.jpg)",
    "stock": 8
  }
]

```

***Caso Especial: Base de datos vacía (HTTP 200 - OK):*** recibe un arreglo vacío, entonces se muestra el mensaje "No hay productos disponibles por el momento".


### 2. Obtener Detalle de Producto

**Método:** GET

**Ruta:** /api/productos/{id}

**Descripción:** Devuelve la información completa de un producto específico utilizando su identificador (ID).

***Request Body:*** Ninguno.

***Caso de Éxito (HTTP 200 - OK):*** El servidor encuentra el producto activo y devuelve su detalle. (Nota: Si el stock es 0, se devuelve igual con valor 0 para que el Frontend muestre "Agotado").
```json
{
  "id": 1,
  "titulo": "Teclado Mecánico RGB",
  "precio": 45000.00,
  "descripcion": "Teclado mecánico con switches red, layout en español y retroiluminación RGB personalizable.",
  "categoria": {
    "id": 3,
    "denominacion": "Periféricos"
  },
  "imagenUrl": "[https://tuservidor.com/imagenes/teclado.jpg](https://tuservidor.com/imagenes/teclado.jpg)",
  "stock": 15
}
```

***Caso de Error:*** Producto inexistente o dado de baja (HTTP 404 - Not Found):
```json
{
  "error": "Not Found",
  "mensaje": "El producto solicitado no existe o ya no está disponible."
}

```

### 3. Confirmar Compra (Checkout)

**Método:** POST

**Ruta:** /api/ordenCompra

**Descripción:** Recibe el resumen del carrito, verifica el stock real en la base de datos, genera la orden, congela los precios históricos, descuenta el stock y devuelve el ticket de confirmación.

***Request Body:*** (Formato JSON)
(Nota de seguridad: Solo se envía el ID y la cantidad. Los precios los calcula el backend consultando a la BD).
```json
{
  "items": [
    {
      "productoId": 1,
      "cantidad": 2
    },
    {
      "productoId": 5,
      "cantidad": 1
    }
  ]
}
```

***Caso de Éxito (HTTP 201 - Created):*** Se devuelve el ID de la orden y el monto final calculado por el servidor para que React pueda mostrar una pantalla de "¡Gracias por tu compra!".
```json
{
  "mensaje": "Compra realizada con éxito",
  "orden": {
    "idOrden": 104,
    "montoTotal": 125000.00,
    "estado": "COMPLETADA"
  }
}
```

***Caso de Error (HTTP 400 - Bad Request):*** Stock insuficiente.
```json
{
  "error": "Bad Request",
  "mensaje": "Stock insuficiente para el producto con ID 1."
}
```