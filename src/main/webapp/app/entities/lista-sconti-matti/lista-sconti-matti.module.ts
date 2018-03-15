import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ScontimattiSharedModule } from '../../shared';
import {
    ListaScontiMattiService,
    ListaScontiMattiPopupService,
    ListaScontiMattiComponent,
    ListaScontiMattiDetailComponent,
    ListaScontiMattiDialogComponent,
    ListaScontiMattiPopupComponent,
    ListaScontiMattiDeletePopupComponent,
    ListaScontiMattiDeleteDialogComponent,
    listaRoute,
    listaPopupRoute,
    ListaScontiMattiResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...listaRoute,
    ...listaPopupRoute,
];

@NgModule({
    imports: [
        ScontimattiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ListaScontiMattiComponent,
        ListaScontiMattiDetailComponent,
        ListaScontiMattiDialogComponent,
        ListaScontiMattiDeleteDialogComponent,
        ListaScontiMattiPopupComponent,
        ListaScontiMattiDeletePopupComponent,
    ],
    entryComponents: [
        ListaScontiMattiComponent,
        ListaScontiMattiDialogComponent,
        ListaScontiMattiPopupComponent,
        ListaScontiMattiDeleteDialogComponent,
        ListaScontiMattiDeletePopupComponent,
    ],
    providers: [
        ListaScontiMattiService,
        ListaScontiMattiPopupService,
        ListaScontiMattiResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ScontimattiListaScontiMattiModule {}
