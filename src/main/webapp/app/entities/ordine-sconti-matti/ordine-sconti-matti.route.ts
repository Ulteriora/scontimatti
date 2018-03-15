import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { OrdineScontiMattiComponent } from './ordine-sconti-matti.component';
import { OrdineScontiMattiDetailComponent } from './ordine-sconti-matti-detail.component';
import { OrdineScontiMattiPopupComponent } from './ordine-sconti-matti-dialog.component';
import { OrdineScontiMattiDeletePopupComponent } from './ordine-sconti-matti-delete-dialog.component';

@Injectable()
export class OrdineScontiMattiResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const ordineRoute: Routes = [
    {
        path: 'ordine-sconti-matti',
        component: OrdineScontiMattiComponent,
        resolve: {
            'pagingParams': OrdineScontiMattiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ordines'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ordine-sconti-matti/:id',
        component: OrdineScontiMattiDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ordines'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ordinePopupRoute: Routes = [
    {
        path: 'ordine-sconti-matti-new',
        component: OrdineScontiMattiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ordines'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ordine-sconti-matti/:id/edit',
        component: OrdineScontiMattiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ordines'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ordine-sconti-matti/:id/delete',
        component: OrdineScontiMattiDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ordines'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
