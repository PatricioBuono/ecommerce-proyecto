import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarritoService } from '../../core/services/carrito';
import { ItemCarrito } from '../../core/models/item-carrito';

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

  constructor(private carritoService: CarritoService) { }

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
}
