import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ListaScontiMatti } from './lista-sconti-matti.model';
import { ListaScontiMattiService } from './lista-sconti-matti.service';

@Component({
    selector: 'jhi-lista-sconti-matti-detail',
    templateUrl: './lista-sconti-matti-detail.component.html'
})
export class ListaScontiMattiDetailComponent implements OnInit, OnDestroy {

    lista: ListaScontiMatti;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private listaService: ListaScontiMattiService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInListas();
    }

    load(id) {
        this.listaService.find(id)
            .subscribe((listaResponse: HttpResponse<ListaScontiMatti>) => {
                this.lista = listaResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInListas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'listaListModification',
            (response) => this.load(this.lista.id)
        );
    }
}
