import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OrdineScontiMatti } from './ordine-sconti-matti.model';
import { OrdineScontiMattiPopupService } from './ordine-sconti-matti-popup.service';
import { OrdineScontiMattiService } from './ordine-sconti-matti.service';

@Component({
    selector: 'jhi-ordine-sconti-matti-dialog',
    templateUrl: './ordine-sconti-matti-dialog.component.html'
})
export class OrdineScontiMattiDialogComponent implements OnInit {

    ordine: OrdineScontiMatti;
    isSaving: boolean;
    dataOrdineDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private ordineService: OrdineScontiMattiService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.ordine.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ordineService.update(this.ordine));
        } else {
            this.subscribeToSaveResponse(
                this.ordineService.create(this.ordine));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<OrdineScontiMatti>>) {
        result.subscribe((res: HttpResponse<OrdineScontiMatti>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: OrdineScontiMatti) {
        this.eventManager.broadcast({ name: 'ordineListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-ordine-sconti-matti-popup',
    template: ''
})
export class OrdineScontiMattiPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ordinePopupService: OrdineScontiMattiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ordinePopupService
                    .open(OrdineScontiMattiDialogComponent as Component, params['id']);
            } else {
                this.ordinePopupService
                    .open(OrdineScontiMattiDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
