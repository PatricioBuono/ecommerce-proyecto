import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ProductoService} from '../../core/services/producto-service';
import { Producto } from '../../core/models/producto';
import { CarritoService } from '../../core/services/carrito-service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home implements OnInit {

  productos: Producto[] = [];

  constructor(
    private productoService: ProductoService,
    private changeDetectorRef: ChangeDetectorRef,
    private carritoService: CarritoService
  ){}

  ngOnInit(): void{
    this.cargarCatalogo();
  }

  cargarCatalogo(): void{
    this.productoService.getProductos().subscribe({
      next: (datosDelBackend) => {
        this.productos = datosDelBackend;
        console.log('Productos cargados exitosammente:', this.productos);
        this.changeDetectorRef.detectChanges();
      },
      error: (error) => {
        console.error('Ocurrió un error al cargar los productos:', error);
      }
    });
  }

  agregarAlCarrito(productoElegido: Producto): void{
    this.carritoService.agregarProducto(productoElegido);
  }

}
