import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ListaScontiMatti } from './lista-sconti-matti.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ListaScontiMatti>;

@Injectable()
export class ListaScontiMattiService {

    private resourceUrl =  SERVER_API_URL + 'api/listas';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(lista: ListaScontiMatti): Observable<EntityResponseType> {
        const copy = this.convert(lista);
        return this.http.post<ListaScontiMatti>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(lista: ListaScontiMatti): Observable<EntityResponseType> {
        const copy = this.convert(lista);
        return this.http.put<ListaScontiMatti>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ListaScontiMatti>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ListaScontiMatti[]>> {
        const options = createRequestOption(req);
        return this.http.get<ListaScontiMatti[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ListaScontiMatti[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ListaScontiMatti = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ListaScontiMatti[]>): HttpResponse<ListaScontiMatti[]> {
        const jsonResponse: ListaScontiMatti[] = res.body;
        const body: ListaScontiMatti[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ListaScontiMatti.
     */
    private convertItemFromServer(lista: ListaScontiMatti): ListaScontiMatti {
        const copy: ListaScontiMatti = Object.assign({}, lista);
        copy.dataOrdine = this.dateUtils
            .convertLocalDateFromServer(lista.dataOrdine);
        return copy;
    }

    /**
     * Convert a ListaScontiMatti to a JSON which can be sent to the server.
     */
    private convert(lista: ListaScontiMatti): ListaScontiMatti {
        const copy: ListaScontiMatti = Object.assign({}, lista);
        copy.dataOrdine = this.dateUtils
            .convertLocalDateToServer(lista.dataOrdine);
        return copy;
    }
}
