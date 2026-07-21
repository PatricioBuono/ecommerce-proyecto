import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AsyncPipe } from '@angular/common';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';
import { CarritoService } from '../../core/services/carrito-service';
import { UsuarioService } from '../../core/services/usuario-service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, AsyncPipe, CommonModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {

  cantidad$: Observable<number>;

  constructor(private carritoService: CarritoService, public usuarioService: UsuarioService) { 
    this.cantidad$ = this.carritoService.cantidadItems$;
  }

  logout(){
    this.usuarioService.cerrarSesion();
  }
}
