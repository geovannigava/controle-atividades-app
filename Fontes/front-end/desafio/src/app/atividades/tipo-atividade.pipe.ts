import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'tipoAtividade'
})

export class TipoAtividadePipe implements PipeTransform {

  transform(value: string, ): string {
    if (value === 'ATENDIMENTO') {
      return 'Atendimento';
    } else if (value === 'DESENVOLVIMENTO') {
      return 'Desenvolvimento';
    } else if (value === 'MANUTENCAO') {
      return 'Manutenção';
    } else if (value === 'MANUTENCAO_URGENTE') {
      return 'Manutenção urgente';
    } else {
      return 'Tipo Desconhecido';
    }
  }

}
