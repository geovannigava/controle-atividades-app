import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { NavBarComponent } from './core/nav-bar/nav-bar.component';
import { CadastroAtividadesComponent } from './atividades/cadastro-atividades/cadastro-atividades.component';

const routes: Routes = [
  { path: '', redirectTo: 'cadastro-atividades', pathMatch: 'full' },
  { path: '**', redirectTo: 'cadastro-atividades' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
