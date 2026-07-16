import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarritoService } from '../../core/services/carrito';
import { ItemCarrito } from '../../core/models/item-carrito';
import { OrdenCompraService } from '../../core/services/orden-compra-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './checkout.html',
  styleUrl: './checkout.css',
})
export class Checkout implements OnInit {

  items: ItemCarrito[] = [];
  totalCompra: number = 0;

  constructor(
    private carritoService: CarritoService,
    private ordenCompraService: OrdenCompraService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.items = this.carritoService.obtenerCarrito();
    this.calcularTotalCompra();
  }

  aumentarCantidad(item: ItemCarrito): void {
    this.carritoService.actualizarCantidad(item.producto.id, item.cantidad + 1);
    this.actualizarVista();
  }

  disminuirCantidad(item: ItemCarrito): void {
    this.carritoService.actualizarCantidad(item.producto.id, item.cantidad - 1);
    this.actualizarVista();
  }

  eliminarItem(idProducto: number): void {
    this.carritoService.eliminarProducto(idProducto);
    this.actualizarVista();
  }

  private actualizarVista(): void {
    this.items = this.carritoService.obtenerCarrito();
    this.calcularTotalCompra();
  }

  calcularTotalCompra(): void {
    this.totalCompra = this.items.reduce((acumulador, item) => {
      return acumulador + (item.producto.precio * item.cantidad);
    }, 0);
  }

  procesarCompra(): void {

    if(this.items.length === 0) return;

    const payload = {
      items: this.items.map(item => ({
        productoId: item.producto.id,
        cantidad: item.cantidad
      })),
      total: this.totalCompra
    };

    this.ordenCompraService.confirmarPedido(payload).subscribe({
      next: (response) => {
        alert(`${response.mensaje}! Tu número de pedido es: ${response.orden.idOrden}`);
        console.log('Compra realizada con éxito:');
        console.log(`ID orden: ${response.orden.idOrden} | Monto total: $${response.orden.montoTotal} | Estado: ${response.orden.estado}`);
        this.carritoService.vaciarCarrito();
        this.router.navigate(['/']);
      },
      error: (error) => {
        console.error('Error al procesar la compra:', error);
        alert('Hubo un error al procesar tu pedido. Por favor, intentá nuevamente');
      }
    });
  }

}
