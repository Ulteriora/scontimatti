/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ScontimattiTestModule } from '../../../test.module';
import { ListaScontiMattiDetailComponent } from '../../../../../../main/webapp/app/entities/lista-sconti-matti/lista-sconti-matti-detail.component';
import { ListaScontiMattiService } from '../../../../../../main/webapp/app/entities/lista-sconti-matti/lista-sconti-matti.service';
import { ListaScontiMatti } from '../../../../../../main/webapp/app/entities/lista-sconti-matti/lista-sconti-matti.model';

describe('Component Tests', () => {

    describe('ListaScontiMatti Management Detail Component', () => {
        let comp: ListaScontiMattiDetailComponent;
        let fixture: ComponentFixture<ListaScontiMattiDetailComponent>;
        let service: ListaScontiMattiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ScontimattiTestModule],
                declarations: [ListaScontiMattiDetailComponent],
                providers: [
                    ListaScontiMattiService
                ]
            })
            .overrideTemplate(ListaScontiMattiDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ListaScontiMattiDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ListaScontiMattiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ListaScontiMatti(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.lista).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
