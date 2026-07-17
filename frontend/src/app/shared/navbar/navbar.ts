import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AsyncPipe } from '@angular/common';
import { Observable } from 'rxjs';
import { CarritoService } from '../../core/services/carrito-service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, AsyncPipe],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {

  cantidad$: Observable<number>;

  constructor(private carritoService: CarritoService) { 
    this.cantidad$ = this.carritoService.cantidadItems$;
  }
}
