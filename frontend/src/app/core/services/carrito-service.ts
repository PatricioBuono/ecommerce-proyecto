import { Injectable } from '@angular/core';
import { ItemCarrito } from '../models/item-carrito';
import { Producto } from '../models/producto';
import { BehaviorSubject } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class CarritoService {

    private items: ItemCarrito[] = [];
    private cantidadItemSubject = new BehaviorSubject<number>(0);

    public cantidadItems$ = this.cantidadItemSubject.asObservable();

    constructor() { }

    agregarProducto(productoNuevo: Producto, cantidad: number = 1): void{

        const itemExistente = this.items.find(item => item.producto.id === productoNuevo.id);
        const cantidadYaEnCarrito = itemExistente ? itemExistente.cantidad : 0;

        if(cantidadYaEnCarrito + cantidad > productoNuevo.stock){
            alert(`No se puede agregar más de ${productoNuevo.stock} unidades de ${productoNuevo.titulo}.`);
            return;
        }

        if(itemExistente){
            itemExistente.cantidad += cantidad;
            console.log(`Sumando cantidad. Ahora hay ${itemExistente.cantidad} de ${productoNuevo.titulo}`);
        }else{
            this.items.push({ producto: productoNuevo, cantidad: cantidad });
            console.log(`Producto nuevo agregado ${productoNuevo.titulo}`);
        }

        this.actualizarContador();
    }

    private actualizarContador(): void{
        const total = this.items.reduce((suma, item) => suma + item.cantidad, 0);
        this.cantidadItemSubject.next(total);
    }

    obtenerCarrito(): ItemCarrito[]{
        return this.items;
    }

    eliminarProducto(idProducto: number): void{
        this.items = this.items.filter(item => item.producto.id !== idProducto);
        this.actualizarContador();
    }

    actualizarCantidad(idProducto: number, nuevaCantidad: number): void{
        const item = this.items.find(i => i.producto.id === idProducto);

        if(item){
            if(nuevaCantidad > 0 && nuevaCantidad <= item.producto.stock){
                item.cantidad = nuevaCantidad;
                this.actualizarContador();
            }else if(nuevaCantidad > item.producto.stock){
                alert(`Stock máximo alcanzado. Solo quedan ${item.producto.stock} unidades de disponibles.`);
            }
        }
    }

    vaciarCarrito(): void{
        this.items = [];
        this.actualizarContador();
    }
    
}
