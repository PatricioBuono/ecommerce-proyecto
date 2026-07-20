export interface UsuarioInfoDTO {
    id: number;
    nombre: string;
    email: string;
    rol: string;
}

export interface UsuarioLoginResponseDTO {
    mensaje: string;
    token: string;
    usuario: UsuarioInfoDTO;

}