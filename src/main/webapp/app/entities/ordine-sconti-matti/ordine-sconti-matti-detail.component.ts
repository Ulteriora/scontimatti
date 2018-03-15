import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { OrdineScontiMatti } from './ordine-sconti-matti.model';
import { OrdineScontiMattiService } from './ordine-sconti-matti.service';

@Component({
    selector: 'jhi-ordine-sconti-matti-detail',
    templateUrl: './ordine-sconti-matti-detail.component.html'
})
export class OrdineScontiMattiDetailComponent implements OnInit, OnDestroy {

    ordine: OrdineScontiMatti;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ordineService: OrdineScontiMattiService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrdines();
    }

    load(id) {
        this.ordineService.find(id)
            .subscribe((ordineResponse: HttpResponse<OrdineScontiMatti>) => {
                this.ordine = ordineResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOrdines() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ordineListModification',
            (response) => this.load(this.ordine.id)
        );
    }
}
