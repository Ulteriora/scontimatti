import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ListaScontiMattiComponent } from './lista-sconti-matti.component';
import { ListaScontiMattiDetailComponent } from './lista-sconti-matti-detail.component';
import { ListaScontiMattiPopupComponent } from './lista-sconti-matti-dialog.component';
import { ListaScontiMattiDeletePopupComponent } from './lista-sconti-matti-delete-dialog.component';

@Injectable()
export class ListaScontiMattiResolvePagingParams implements Resolve<any> {

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

export const listaRoute: Routes = [
    {
        path: 'lista-sconti-matti',
        component: ListaScontiMattiComponent,
        resolve: {
            'pagingParams': ListaScontiMattiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Listas'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'lista-sconti-matti/:id',
        component: ListaScontiMattiDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Listas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const listaPopupRoute: Routes = [
    {
        path: 'lista-sconti-matti-new',
        component: ListaScontiMattiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Listas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lista-sconti-matti/:id/edit',
        component: ListaScontiMattiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Listas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lista-sconti-matti/:id/delete',
        component: ListaScontiMattiDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Listas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
