import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ScontimattiSharedModule } from '../../shared';
import {
    OrdineScontiMattiService,
    OrdineScontiMattiPopupService,
    OrdineScontiMattiComponent,
    OrdineScontiMattiDetailComponent,
    OrdineScontiMattiDialogComponent,
    OrdineScontiMattiPopupComponent,
    OrdineScontiMattiDeletePopupComponent,
    OrdineScontiMattiDeleteDialogComponent,
    ordineRoute,
    ordinePopupRoute,
    OrdineScontiMattiResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...ordineRoute,
    ...ordinePopupRoute,
];

@NgModule({
    imports: [
        ScontimattiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OrdineScontiMattiComponent,
        OrdineScontiMattiDetailComponent,
        OrdineScontiMattiDialogComponent,
        OrdineScontiMattiDeleteDialogComponent,
        OrdineScontiMattiPopupComponent,
        OrdineScontiMattiDeletePopupComponent,
    ],
    entryComponents: [
        OrdineScontiMattiComponent,
        OrdineScontiMattiDialogComponent,
        OrdineScontiMattiPopupComponent,
        OrdineScontiMattiDeleteDialogComponent,
        OrdineScontiMattiDeletePopupComponent,
    ],
    providers: [
        OrdineScontiMattiService,
        OrdineScontiMattiPopupService,
        OrdineScontiMattiResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ScontimattiOrdineScontiMattiModule {}
