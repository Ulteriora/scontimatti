import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { ListaScontiMatti } from './lista-sconti-matti.model';
import { ListaScontiMattiService } from './lista-sconti-matti.service';

@Injectable()
export class ListaScontiMattiPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private listaService: ListaScontiMattiService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.listaService.find(id)
                    .subscribe((listaResponse: HttpResponse<ListaScontiMatti>) => {
                        const lista: ListaScontiMatti = listaResponse.body;
                        if (lista.dataOrdine) {
                            lista.dataOrdine = {
                                year: lista.dataOrdine.getFullYear(),
                                month: lista.dataOrdine.getMonth() + 1,
                                day: lista.dataOrdine.getDate()
                            };
                        }
                        this.ngbModalRef = this.listaModalRef(component, lista);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.listaModalRef(component, new ListaScontiMatti());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    listaModalRef(component: Component, lista: ListaScontiMatti): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.lista = lista;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
