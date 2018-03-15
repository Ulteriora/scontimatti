/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ScontimattiTestModule } from '../../../test.module';
import { OrdineScontiMattiDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/ordine-sconti-matti/ordine-sconti-matti-delete-dialog.component';
import { OrdineScontiMattiService } from '../../../../../../main/webapp/app/entities/ordine-sconti-matti/ordine-sconti-matti.service';

describe('Component Tests', () => {

    describe('OrdineScontiMatti Management Delete Component', () => {
        let comp: OrdineScontiMattiDeleteDialogComponent;
        let fixture: ComponentFixture<OrdineScontiMattiDeleteDialogComponent>;
        let service: OrdineScontiMattiService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ScontimattiTestModule],
                declarations: [OrdineScontiMattiDeleteDialogComponent],
                providers: [
                    OrdineScontiMattiService
                ]
            })
            .overrideTemplate(OrdineScontiMattiDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrdineScontiMattiDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdineScontiMattiService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
