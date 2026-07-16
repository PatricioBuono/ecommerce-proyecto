export interface OrdenDTO {
    idOrden: number;
    montoTotal: number;
    estado: string;
}

export interface OrdenCompraResponseDTO {
    mensaje: string;
    orden: OrdenDTO;
}

