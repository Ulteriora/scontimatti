/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ScontimattiTestModule } from '../../../test.module';
import { ListaScontiMattiDialogComponent } from '../../../../../../main/webapp/app/entities/lista-sconti-matti/lista-sconti-matti-dialog.component';
import { ListaScontiMattiService } from '../../../../../../main/webapp/app/entities/lista-sconti-matti/lista-sconti-matti.service';
import { ListaScontiMatti } from '../../../../../../main/webapp/app/entities/lista-sconti-matti/lista-sconti-matti.model';

describe('Component Tests', () => {

    describe('ListaScontiMatti Management Dialog Component', () => {
        let comp: ListaScontiMattiDialogComponent;
        let fixture: ComponentFixture<ListaScontiMattiDialogComponent>;
        let service: ListaScontiMattiService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ScontimattiTestModule],
                declarations: [ListaScontiMattiDialogComponent],
                providers: [
                    ListaScontiMattiService
                ]
            })
            .overrideTemplate(ListaScontiMattiDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ListaScontiMattiDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ListaScontiMattiService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ListaScontiMatti(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.lista = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'listaListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ListaScontiMatti();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.lista = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'listaListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
