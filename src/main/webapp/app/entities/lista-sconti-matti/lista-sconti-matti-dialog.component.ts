import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ListaScontiMatti } from './lista-sconti-matti.model';
import { ListaScontiMattiPopupService } from './lista-sconti-matti-popup.service';
import { ListaScontiMattiService } from './lista-sconti-matti.service';

@Component({
    selector: 'jhi-lista-sconti-matti-dialog',
    templateUrl: './lista-sconti-matti-dialog.component.html'
})
export class ListaScontiMattiDialogComponent implements OnInit {

    lista: ListaScontiMatti;
    isSaving: boolean;
    dataOrdineDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private listaService: ListaScontiMattiService,
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
        if (this.lista.id !== undefined) {
            this.subscribeToSaveResponse(
                this.listaService.update(this.lista));
        } else {
            this.subscribeToSaveResponse(
                this.listaService.create(this.lista));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ListaScontiMatti>>) {
        result.subscribe((res: HttpResponse<ListaScontiMatti>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ListaScontiMatti) {
        this.eventManager.broadcast({ name: 'listaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-lista-sconti-matti-popup',
    template: ''
})
export class ListaScontiMattiPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private listaPopupService: ListaScontiMattiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.listaPopupService
                    .open(ListaScontiMattiDialogComponent as Component, params['id']);
            } else {
                this.listaPopupService
                    .open(ListaScontiMattiDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
