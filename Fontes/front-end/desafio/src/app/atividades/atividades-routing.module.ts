import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

import { CadastroAtividadesComponent } from './cadastro-atividades/cadastro-atividades.component';
import { DialogBoxComponent } from './dialog-box/dialog-box.component';

const routes: Routes = [
  {
    path: 'cadastro-atividades',
    component: CadastroAtividadesComponent
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class AtividadesRoutingModule { }
