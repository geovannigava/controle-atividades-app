import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


import { environment } from './../../environments/environment';
import { ErrorHandlerService } from '../core/error-handler.service';


@Injectable({
  providedIn: 'root'
})
export class AtividadesService {

  atividadesUrl: string;

  constructor( private http: HttpClient,
    private erroHandlerService: ErrorHandlerService) {
    this.atividadesUrl = `${environment.apiUrl}/desafio/atividade/`;
   }

 	/**
	 * Busca todas as atividades no banco de dados
	 *
	 * @return Observable<any>
	 */
  buscarTodasAtividades(): Observable<any> {
    let dataObserver;
    let data = new Observable(observer => (dataObserver = observer));
    this.http
      .get(this.atividadesUrl)
      .subscribe(dados => {
        if (dataObserver != null) {
          dataObserver.next(dados);
        }
      }, err => {
        this.erroHandlerService.handle(err);
      });
    return data;
  }

  /**
	 * Busca as Atividade no banco de dados por Status da atividade
	 * 0 - Atividades Pendentes
	 * 1 - Atividades Concluídas
	 *
	 * @param status
	 * @return Observable<any>
	 */
  buscarAtividadesPorStatus(status: string): Observable<any> {
    let dataObserver;
    let data = new Observable(observer => (dataObserver = observer));
    let params = new HttpParams().set('statusAtividade', status);
    this.http
      .get(this.atividadesUrl, {
        params: params,
      })
      .subscribe(dados => {
        if (dataObserver != null) {
          dataObserver.next(dados);
        }
      }, err => {
        this.erroHandlerService.handle(err);
      });
    return data;
  }

  /**
	 * Grava atividade no banco de dados
	 *
	 * @param body
	 * @return Observable<any>
	 */
  salvarAtividade(body?: any): Observable<any> {
    let dataObserver;
    let data = new Observable(observer => (dataObserver = observer));
    let bodyJson = JSON.stringify(body);
    this.http
      .post(this.atividadesUrl, bodyJson)
      .subscribe(dados => {

        if (dataObserver != null) {
          dataObserver.next(dados);
        }
      }, err => {
        this.erroHandlerService.handle(err);
      });
    return data;
  }

  /**
	 * Atualiza a Atividade no banco de dados
	 *
	 * @param path
   * @param body
	 * @return Observable<any>
	 */
  atualizarAtividade(path: number, body?: any): Observable<any> {
    let dataObserver;
    let data = new Observable(observer => (dataObserver = observer));
    let bodyJson = JSON.stringify(body);
    this.http
      .put(this.atividadesUrl + path, bodyJson)
      .subscribe(dados => {
        if (dataObserver != null) {
          dataObserver.next(dados);
        }
      }, err => {
        this.erroHandlerService.handle(err);
      });

    return data;
  }

  /**
	 * Busca Atividade no banco de dados por id
	 *
	 * @param path
	 * @return Observable<any>
	 */
  buscarAtividade(path: number): Observable<any> {
    let dataObserver;
    let data = new Observable(observer => (dataObserver = observer));
    this.http
      .get(this.atividadesUrl + path)
      .subscribe(dados => {
        if (dataObserver != null) {
          dataObserver.next(dados);
        }
      }, err => {
        this.erroHandlerService.handle(err);
      });
    return data;
  }


 	/**
	 * Exclui Atividade no banco de dados
	 *
	 * @param path
	 * @return Observable<any>
	 */
  excluirAtividade(path: number): Observable<any> {
    let dataObserver;
    let data = new Observable(observer => (dataObserver = observer));
    this.http.delete(this.atividadesUrl + path).subscribe(dados => {
      if (dataObserver != null) {
        dataObserver.next(dados);
      }
    }, err => {
      this.erroHandlerService.handle(err);
    });
    return data;
  }

}
