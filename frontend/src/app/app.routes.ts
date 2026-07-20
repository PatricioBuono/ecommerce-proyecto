import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Checkout } from './pages/checkout/checkout';
import { Registro } from './pages/auth/registro/registro';
import { Login } from './pages/auth/login/login';

export const routes: Routes = [
  { path: '', component: Home }, // Ruta raíz (localhost:4200) mapea al Home
  { path: 'checkout', component: Checkout }, // localhost:4200/checkout mapea al Checkout
  { path: 'auth/registro', component: Registro }, // localhost:4200/auth/registro mapea al Registro
  { path: 'auth/login', component: Login}, // localhost:4200/auth/login mapea al Login
  { path: '**', redirectTo: '', pathMatch: 'full' } // Catch-all: cualquier URL inválida redirige al Home
];