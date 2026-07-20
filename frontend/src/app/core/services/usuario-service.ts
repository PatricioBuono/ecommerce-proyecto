import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UsuarioRegistroRequestDTO } from '../models/usuario-registro-request';
import { UsuarioRegistroResponseDTO } from '../models/usuario-registro-response';
import { UsuarioLoginRequestDTO } from '../models/usuario-login-request';
import { UsuarioLoginResponseDTO } from '../models/usuario-login-response';

@Injectable({
    providedIn: 'root'
})  
export class UsuarioService{

    private apiUrl = 'http://localhost:8080/api/auth';

    constructor(private http: HttpClient){}

    registrarUsuario(datosUsuario: UsuarioRegistroRequestDTO): Observable<UsuarioRegistroResponseDTO>{
        return this.http.post<UsuarioRegistroResponseDTO>(`${this.apiUrl}/registro`, datosUsuario);
    }

    iniciarSesion(credenciales: UsuarioLoginRequestDTO): Observable<UsuarioLoginResponseDTO>{
        return this.http.post<UsuarioLoginResponseDTO>(`${this.apiUrl}/login`, credenciales);
    }
}