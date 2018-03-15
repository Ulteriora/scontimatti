/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ScontimattiTestModule } from '../../../test.module';
import { ListaScontiMattiDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/lista-sconti-matti/lista-sconti-matti-delete-dialog.component';
import { ListaScontiMattiService } from '../../../../../../main/webapp/app/entities/lista-sconti-matti/lista-sconti-matti.service';

describe('Component Tests', () => {

    describe('ListaScontiMatti Management Delete Component', () => {
        let comp: ListaScontiMattiDeleteDialogComponent;
        let fixture: ComponentFixture<ListaScontiMattiDeleteDialogComponent>;
        let service: ListaScontiMattiService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ScontimattiTestModule],
                declarations: [ListaScontiMattiDeleteDialogComponent],
                providers: [
                    ListaScontiMattiService
                ]
            })
            .overrideTemplate(ListaScontiMattiDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ListaScontiMattiDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ListaScontiMattiService);
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
