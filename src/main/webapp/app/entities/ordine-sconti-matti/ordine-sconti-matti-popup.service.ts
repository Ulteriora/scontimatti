import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { OrdineScontiMatti } from './ordine-sconti-matti.model';
import { OrdineScontiMattiService } from './ordine-sconti-matti.service';

@Injectable()
export class OrdineScontiMattiPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private ordineService: OrdineScontiMattiService

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
                this.ordineService.find(id)
                    .subscribe((ordineResponse: HttpResponse<OrdineScontiMatti>) => {
                        const ordine: OrdineScontiMatti = ordineResponse.body;
                        if (ordine.dataOrdine) {
                            ordine.dataOrdine = {
                                year: ordine.dataOrdine.getFullYear(),
                                month: ordine.dataOrdine.getMonth() + 1,
                                day: ordine.dataOrdine.getDate()
                            };
                        }
                        this.ngbModalRef = this.ordineModalRef(component, ordine);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.ordineModalRef(component, new OrdineScontiMatti());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    ordineModalRef(component: Component, ordine: OrdineScontiMatti): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.ordine = ordine;
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
