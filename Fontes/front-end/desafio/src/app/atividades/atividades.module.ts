import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule } from '@angular/forms';

import { MatButtonModule, MatFormFieldModule,
  MatInputModule, MatRippleModule, MatGridListModule, MatDividerModule, MatCardModule,
  MatSelectModule, MatTableModule, MatIconModule, MatCheckboxModule, MatSortModule, MatRadioModule, MatSnackBarModule, MatDialogModule, MatTooltipModule, MatPaginatorModule, MatPaginatorIntl } from '@angular/material';

import { CadastroAtividadesComponent } from './cadastro-atividades/cadastro-atividades.component';
import { AtividadesRoutingModule } from './atividades-routing.module';
import { DialogBoxComponent } from './dialog-box/dialog-box.component';
import { CustomMatPaginatorIntl } from './CustomMatPaginatorIntl';
import { TipoAtividadePipe } from './tipo-atividade.pipe';
import { StatusAtividadePipe } from './status-atividade.pipe';


@NgModule({
  declarations: [
    CadastroAtividadesComponent,
    DialogBoxComponent,
    TipoAtividadePipe,
    StatusAtividadePipe
  ],
  imports: [
    CommonModule,
    AtividadesRoutingModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatRippleModule,
    MatGridListModule,
    MatDividerModule,
    FlexLayoutModule,
    MatCardModule,
    MatSelectModule,
    FormsModule,
    MatTableModule,
    MatIconModule,
    MatCheckboxModule,
    MatSortModule,
    MatRadioModule,
    HttpClientModule,
    MatSnackBarModule,
    MatDialogModule,
    MatTooltipModule,
    MatPaginatorModule
  ],
  exports: [
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatRippleModule,
    MatGridListModule,
    MatDividerModule,
    FlexLayoutModule,
    MatCardModule,
    MatSelectModule,
    MatTableModule,
    MatDialogModule
  ],
  entryComponents: [DialogBoxComponent],
  providers: [
   {provide: MatPaginatorIntl, useClass: CustomMatPaginatorIntl}
 ]
})
export class AtividadesModule { }
