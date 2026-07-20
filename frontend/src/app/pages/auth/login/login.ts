import { Component, ChangeDetectorRef } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { UsuarioLoginRequestDTO } from "../../../core/models/usuario-login-request";
import { UsuarioService } from "../../../core/services/usuario-service";
import { Router } from "@angular/router";
import { ErrorResponse } from "../../../core/models/error-response";
import { RouterModule } from "@angular/router";


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})  
export class Login {

    inicioSesion: UsuarioLoginRequestDTO = {
        email: '',
        password: ''
    };

    constructor(
        private usuarioService: UsuarioService,
        private router: Router,
        private cdr: ChangeDetectorRef
    ) {}

    errores = {
        email: '',
        password: ''
    };

    iniciarSesion(): void {
        this.errores = { email: '', password: ''};

        this.usuarioService.iniciarSesion(this.inicioSesion).subscribe({
            next: (response) => {
                console.log('Login exitoso', response);

                // TODO: Acá más adelante vamos a guardar el "response.token" 
                // para mantener la sesión abierta. Por ahora solo redirigimos.

                this.router.navigate(['/']);
            },
            error: (err) => {
                
                if (err.status === 400 && err.error?.campo){
                          
                    const errorResponse = err.error as ErrorResponse;
                          
                    this.errores[errorResponse.campo as keyof typeof this.errores] = errorResponse.mensaje;
                    this.cdr.detectChanges();
                
                    console.log(err.error);
                } else{
                
                    alert("Ocurrió un error inesperado.");
                    console.error('Error de login: ', err);
                }
            }
        });
    }
}