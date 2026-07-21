import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import Swal from 'sweetalert2';


export const authGuard: CanActivateFn = (route, state) => {

    const router = inject(Router);
    const token = localStorage.getItem('token');

    if(token){
        return true;
    } else {
        Swal.fire({
            icon: 'warning',
            title: 'Acceso requerido',
            text: '¡Debes iniciar sesión para acceder al carrito de compras!',
            confirmButtonText: 'Ir a Iniciar sesión',
            confirmButtonColor: '#0d6efd'
        }).then(() => {
            router.navigate(['/auth/login']);
        });        
        return false;
    }
};