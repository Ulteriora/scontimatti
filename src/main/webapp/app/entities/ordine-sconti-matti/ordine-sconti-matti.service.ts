import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { OrdineScontiMatti } from './ordine-sconti-matti.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<OrdineScontiMatti>;

@Injectable()
export class OrdineScontiMattiService {

    private resourceUrl =  SERVER_API_URL + 'api/ordines';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(ordine: OrdineScontiMatti): Observable<EntityResponseType> {
        const copy = this.convert(ordine);
        return this.http.post<OrdineScontiMatti>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(ordine: OrdineScontiMatti): Observable<EntityResponseType> {
        const copy = this.convert(ordine);
        return this.http.put<OrdineScontiMatti>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<OrdineScontiMatti>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<OrdineScontiMatti[]>> {
        const options = createRequestOption(req);
        return this.http.get<OrdineScontiMatti[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<OrdineScontiMatti[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: OrdineScontiMatti = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<OrdineScontiMatti[]>): HttpResponse<OrdineScontiMatti[]> {
        const jsonResponse: OrdineScontiMatti[] = res.body;
        const body: OrdineScontiMatti[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to OrdineScontiMatti.
     */
    private convertItemFromServer(ordine: OrdineScontiMatti): OrdineScontiMatti {
        const copy: OrdineScontiMatti = Object.assign({}, ordine);
        copy.dataOrdine = this.dateUtils
            .convertLocalDateFromServer(ordine.dataOrdine);
        return copy;
    }

    /**
     * Convert a OrdineScontiMatti to a JSON which can be sent to the server.
     */
    private convert(ordine: OrdineScontiMatti): OrdineScontiMatti {
        const copy: OrdineScontiMatti = Object.assign({}, ordine);
        copy.dataOrdine = this.dateUtils
            .convertLocalDateToServer(ordine.dataOrdine);
        return copy;
    }
}
