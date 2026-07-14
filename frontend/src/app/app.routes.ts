import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Checkout } from './pages/checkout/checkout';

export const routes: Routes = [
  { path: '', component: Home }, // Ruta raíz (localhost:4200) mapea al Home
  { path: 'checkout', component: Checkout }, // localhost:4200/checkout mapea al Checkout
  { path: '**', redirectTo: '', pathMatch: 'full' } // Catch-all: cualquier URL inválida redirige al Home
];