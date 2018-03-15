import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ListaScontiMatti } from './lista-sconti-matti.model';
import { ListaScontiMattiPopupService } from './lista-sconti-matti-popup.service';
import { ListaScontiMattiService } from './lista-sconti-matti.service';

@Component({
    selector: 'jhi-lista-sconti-matti-delete-dialog',
    templateUrl: './lista-sconti-matti-delete-dialog.component.html'
})
export class ListaScontiMattiDeleteDialogComponent {

    lista: ListaScontiMatti;

    constructor(
        private listaService: ListaScontiMattiService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.listaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'listaListModification',
                content: 'Deleted an lista'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lista-sconti-matti-delete-popup',
    template: ''
})
export class ListaScontiMattiDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private listaPopupService: ListaScontiMattiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.listaPopupService
                .open(ListaScontiMattiDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
