/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ScontimattiTestModule } from '../../../test.module';
import { ListaScontiMattiComponent } from '../../../../../../main/webapp/app/entities/lista-sconti-matti/lista-sconti-matti.component';
import { ListaScontiMattiService } from '../../../../../../main/webapp/app/entities/lista-sconti-matti/lista-sconti-matti.service';
import { ListaScontiMatti } from '../../../../../../main/webapp/app/entities/lista-sconti-matti/lista-sconti-matti.model';

describe('Component Tests', () => {

    describe('ListaScontiMatti Management Component', () => {
        let comp: ListaScontiMattiComponent;
        let fixture: ComponentFixture<ListaScontiMattiComponent>;
        let service: ListaScontiMattiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ScontimattiTestModule],
                declarations: [ListaScontiMattiComponent],
                providers: [
                    ListaScontiMattiService
                ]
            })
            .overrideTemplate(ListaScontiMattiComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ListaScontiMattiComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ListaScontiMattiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ListaScontiMatti(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.listas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
