import { Component, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { UsuarioRegistroRequestDTO } from '../../../core/models/usuario-registro-request';
import { UsuarioService } from '../../../core/services/usuario-service';
import { ErrorResponse } from '../../../core/models/error-response';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './registro.html',
  styleUrls: ['./registro.css']
})  
export class Registro{

  registroUsuario: UsuarioRegistroRequestDTO = {
    nombre: '',
    apellido: '',
    email: '',
    password: ''
  };

  constructor(
    private usuarioService: UsuarioService, 
    private router: Router, 
    private cdr: ChangeDetectorRef
  ) {}

  errores = {
    nombre: '',
    apellido: '',
    email: '',
    password: ''
  };

  registrarUsuario(): void {

    this.errores = { nombre: '', apellido: '', email: '', password: '' };


    this.usuarioService.registrarUsuario(this.registroUsuario).subscribe({
      next: (response) => {
        console.log('Usuario registrado con éxito:', response);
        alert('Cuenta creada con éxito. ¡Ya podés iniciar sesión!');
        this.router.navigate(['/auth/login']);
      },
      error: (err) => {

        if (err.status === 400 && err.error?.campo){
          
          const errorResponse = err.error as ErrorResponse;
          
          this.errores[errorResponse.campo as keyof typeof this.errores] = errorResponse.mensaje;
          this.cdr.detectChanges();

          console.log(err.error);
        } else{

          alert("Ocurrió un error inesperado.");
          console.error(err);
        }
      }
    });
  }
}