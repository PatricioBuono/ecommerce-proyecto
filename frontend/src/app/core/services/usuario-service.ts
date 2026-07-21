import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
import { Router } from '@angular/router';
import { UsuarioRegistroRequestDTO } from '../models/usuario-registro-request';
import { UsuarioRegistroResponseDTO } from '../models/usuario-registro-response';
import { UsuarioLoginRequestDTO } from '../models/usuario-login-request';
import { UsuarioLoginResponseDTO } from '../models/usuario-login-response';

@Injectable({
    providedIn: 'root'
})  
export class UsuarioService{

    private apiUrl = 'http://localhost:8080/api/auth';
    private usuarioActual = new BehaviorSubject<any>(this.obtenerUsuarioLocal());

    public usuario$ = this.usuarioActual.asObservable();

    constructor(private http: HttpClient, private router: Router){}

    registrarUsuario(datosUsuario: UsuarioRegistroRequestDTO): Observable<UsuarioRegistroResponseDTO>{
        return this.http.post<UsuarioRegistroResponseDTO>(`${this.apiUrl}/registro`, datosUsuario);
    }

    iniciarSesion(credenciales: UsuarioLoginRequestDTO): Observable<UsuarioLoginResponseDTO>{
        return this.http.post<UsuarioLoginResponseDTO>(`${this.apiUrl}/login`, credenciales);
    }

    private obtenerUsuarioLocal(){
        const usuarioString = localStorage.getItem('usuario');
        return usuarioString ? JSON.parse(usuarioString) : null;
    }

    actualizarSesion(usuario: any){
        this.usuarioActual.next(usuario);
    }

    cerrarSesion(){
        localStorage.removeItem('token');
        localStorage.removeItem('usuario');
        this.usuarioActual.next(null);
        this.router.navigate(['/auth/login']);
    }
}