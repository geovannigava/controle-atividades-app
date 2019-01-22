import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastroAtividadesComponent } from './cadastro-atividades.component';

describe('CadastroAtividadesComponent', () => {
  let component: CadastroAtividadesComponent;
  let fixture: ComponentFixture<CadastroAtividadesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CadastroAtividadesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CadastroAtividadesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
