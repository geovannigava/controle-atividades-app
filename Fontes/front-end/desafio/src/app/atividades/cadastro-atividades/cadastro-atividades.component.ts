import { Component, OnInit, ChangeDetectorRef, ViewChild, Input } from '@angular/core';
import { SelectionModel, DataSource } from '@angular/cdk/collections';
import { MatTableDataSource, MatSort, MatSortable, throwMatDialogContentAlreadyAttachedError, MatSnackBar, MatDialog, MatDialogConfig, MatPaginator } from '@angular/material';

import { Atividade, SelectTipoAtividade, SelectStatusAtividade } from 'src/app/core/model';
import { AtividadesService } from '../atividades.service';
import { Observable, BehaviorSubject } from 'rxjs';
import { FormControl, NgForm } from '@angular/forms';

import { DialogBoxComponent } from './../dialog-box/dialog-box.component';

@Component({
  selector: 'app-cadastro-atividades',
  templateUrl: './cadastro-atividades.component.html',
  styleUrls: ['./cadastro-atividades.component.css']
})
export class CadastroAtividadesComponent implements OnInit {

  constructor(private atividadesService: AtividadesService,
    private changeDetectorRefs: ChangeDetectorRef,
    public snackBar: MatSnackBar,
    public dialog: MatDialog) { }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  atividade = new Atividade();
  atividades = [];
  tipoAtividadeSelecionada = 'ATENDIMENTO';
  statusAtividadeSelecionada = 'PENDENTE';
  tipoAtividades: SelectTipoAtividade[] = [
    { value: 'DESENVOLVIMENTO', viewValue: 'Desenvolvimento' },
    { value: 'ATENDIMENTO', viewValue: 'Atendimento' },
    { value: 'MANUTENCAO', viewValue: 'Manutenção' },
    { value: 'MANUTENCAO_URGENTE', viewValue: 'Manutenção urgente' }
  ];
  statusAtividades: SelectStatusAtividade[] = [
    { value: 'PENDENTE', viewValue: 'Pendente' },
    { value: 'CONCLUIDA', viewValue: 'Concluída' },
  ];
  rotuloColunasTable: string[] = ['idAtividade', 'titulo', 'descricao',
    'tipoAtividade', 'status', 'editar', 'excluir', 'finalizar'];
  dataSource: MatTableDataSource<Atividade>;
  tiposExibicao = [
    { value: '0', descricao: 'Atividades Pendentes' },
    { value: '1', descricao: 'Atividades Concluídas' },
    { value: '2', descricao: 'Todas as Atividades' }
  ];
  exibicaoSelecionada = '2';

  ngOnInit() {
    this.carregarTodasAtividades();
  }

  salvarAtividade(form: FormControl) {
    let atividadeSalva: Atividade;
    this.atividade.tipoAtividade = this.tipoAtividadeSelecionada;
    this.atividade.status = this.statusAtividadeSelecionada;
    if (this.verificaSextaFeira(this.atividade)) {
      this.openSnackBar('Manutenções urgentes não podem ser criadas após as 13:00 de sexta-feira. ^^',
        'Operação não Permitida');
      this.atividade = new Atividade();
      form.reset();
    } else {
      this.atividadesService.salvarAtividade(this.atividade).subscribe((retorno: Atividade) => {
        atividadeSalva = retorno;
        if (atividadeSalva.idAtividade != null) {
          this.openSnackBar('Atividade Criada', 'Sucesso');
          this.carregarTodasAtividades();
        }
      });
      form.reset();
    }


  }

  private carregarTodasAtividades() {
    this.atividadesService.buscarTodasAtividades().subscribe((retorno: Atividade[]) => {
      this.atividades = retorno;
      this.dataSource = new MatTableDataSource<Atividade>(retorno);
      this.dataSource.paginator = this.paginator;
      this.atividade = new Atividade();
    });
    this.changeDetectorRefs.detectChanges();
  }

  selecionarAtividade(atividade: Atividade) {
    this.atividadesService.buscarAtividade(atividade.idAtividade).subscribe((retorno: Atividade) => {
      this.atividade = retorno;
      this.tipoAtividadeSelecionada = this.atividade.tipoAtividade;
      this.statusAtividadeSelecionada = this.atividade.status;
    });
  }

  atualizarAtividade(idAtividade: number, form: FormControl) {
    this.atividade.tipoAtividade = this.tipoAtividadeSelecionada;
    if (this.verificaSextaFeira(this.atividade)) {
      this.openSnackBar('Manutenções urgentes não podem ser criadas após as 13:00 de sexta-feira. ^^',
        'Operação não Permitida');
      this.atividade = new Atividade();
      form.reset();
    } else {
      this.atividadesService.atualizarAtividade(idAtividade, this.atividade)
        .subscribe((retorno: boolean) => {
          this.atividade = new Atividade();
          this.carregarTodasAtividades();
          if (retorno === true) {
            this.openSnackBar('Atividade Alterada', 'Sucesso');
          } else {
            this.openSnackBar('Para finalizar essa atividade preencha o campo "Descrição" com pelo menos 50 caracteres',
          'Operação não Permitida');
          }
        });
      form.reset();
    }

  }

  excluirAtividade(idAtividade: number) {
    this.atividadesService.excluirAtividade(idAtividade)
      .subscribe((retorno: string) => {
        this.openSnackBar('Atividade Excluída', 'Sucesso');
        this.carregarTodasAtividades();
      });
  }

  verificaStatus(status: string): boolean {
    if (status === 'PENDENTE') {
      return true;
    } else {
      return false;
    }
  }

  atualizarStatusAtividade(atividade: Atividade) {
    if (atividade.status === 'PENDENTE') {
      if (this.verificaTamanhoDescricao(atividade)) {
        this.openSnackBar('Para finalizar essa atividade preencha o campo "Descrição" com pelo menos 50 caracteres',
          'Operação não Permitida');
      } else {
        atividade.status = 'CONCLUIDA';
        this.atividadesService.atualizarAtividade(atividade.idAtividade, atividade).subscribe((retorno: Atividade) => {
          this.atividade = new Atividade();
          this.openSnackBar('Status alterado', 'Sucesso');
          this.carregarTodasAtividades();
        });
      }
    } else {
      atividade.status = 'PENDENTE';
      this.atividadesService.atualizarAtividade(atividade.idAtividade, atividade).subscribe((retorno: Atividade) => {
        this.atividade = new Atividade();
        this.openSnackBar('Status alterado', 'Sucesso');
        this.carregarTodasAtividades();
      });
    }
  }

  aplicarFiltro(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }

  buscarAtividadesPorStatus(tpExibicao: string) {
    if (tpExibicao === '2') {
      this.carregarTodasAtividades();
    } else {
      this.atividadesService.buscarAtividadesPorStatus(tpExibicao).subscribe((retorno: Atividade[]) => {
        this.atividades = retorno;
        this.dataSource = new MatTableDataSource<Atividade>(retorno);
        this.dataSource.paginator = this.paginator;
        this.atividade = new Atividade();
      });
      this.changeDetectorRefs.detectChanges();
    }
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 3000,
    });
  }

  dialogoConfirmacaoExclusao(atividade: Atividade): void {
    if (atividade.tipoAtividade === 'MANUTENCAO_URGENTE') {
      this.openSnackBar('Atividades de Manutenção Urgente não podem ser Removidas',
        'Operação não Permitida');
    } else {
      const dialogRef = this.dialog.open(DialogBoxComponent, {
        width: '250px',
        data: {}
      });
      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          this.excluirAtividade(atividade.idAtividade);
        }
      });
    }
  }

  verificaTamanhoDescricao(atividade: Atividade): boolean {
    if (atividade.tipoAtividade === 'MANUTENCAO_URGENTE' ||
      atividade.tipoAtividade === 'ATENDIMENTO') {
      if (atividade.status === 'PENDENTE') {
        if (atividade.descricao.length < 50) {
          return true;
        }
      }
    } else {
      return false;
    }
  }

  verificaSextaFeira(atividade: Atividade): boolean {
    let dataAtual = new Date();
    if (atividade.tipoAtividade === 'MANUTENCAO_URGENTE' && dataAtual.getDay() === 5 &&
      dataAtual.getHours() >= 13 && dataAtual.getHours() <= 23) {
      return true;
    } else {
      return false;
    }
  }

}

