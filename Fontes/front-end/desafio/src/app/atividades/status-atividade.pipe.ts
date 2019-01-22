import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'statusAtividade'
})
export class StatusAtividadePipe implements PipeTransform {

  transform(value: string, ): string {
    if (value === 'PENDENTE') {
      return 'Pendente';
    } else if (value === 'CONCLUIDA') {
      return 'Concluída';
    } else {
      return 'Status Desconhecido';
    }
  }

}
