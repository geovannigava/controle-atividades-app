export class Atividade {
  idAtividade: number;
  descricao: string;
  titulo: string;
  tipoAtividade: string;
  status: string;
}

export interface SelectTipoAtividade {
  value: string;
  viewValue: string;
}

export interface SelectStatusAtividade {
  value: string;
  viewValue: string;
}


