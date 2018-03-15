import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OrdineScontiMatti } from './ordine-sconti-matti.model';
import { OrdineScontiMattiPopupService } from './ordine-sconti-matti-popup.service';
import { OrdineScontiMattiService } from './ordine-sconti-matti.service';

@Component({
    selector: 'jhi-ordine-sconti-matti-delete-dialog',
    templateUrl: './ordine-sconti-matti-delete-dialog.component.html'
})
export class OrdineScontiMattiDeleteDialogComponent {

    ordine: OrdineScontiMatti;

    constructor(
        private ordineService: OrdineScontiMattiService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ordineService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ordineListModification',
                content: 'Deleted an ordine'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ordine-sconti-matti-delete-popup',
    template: ''
})
export class OrdineScontiMattiDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ordinePopupService: OrdineScontiMattiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ordinePopupService
                .open(OrdineScontiMattiDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
