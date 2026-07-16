import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OrdenCompraResponseDTO } from '../models/orden-compra-response';

@Injectable({
    providedIn: 'root'
})
export class OrdenCompraService {

    private apiUrl = 'http://localhost:8080/api/ordenCompra';

    constructor(private http: HttpClient) { }

    confirmarPedido(pedido: any): Observable<OrdenCompraResponseDTO> {
        return this.http.post<OrdenCompraResponseDTO>(this.apiUrl, pedido);
    }
    
}
