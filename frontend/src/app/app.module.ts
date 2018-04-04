import { BrowserModule } from '@angular/platform-browser';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import {
    MatButtonModule,
    MatCardModule,
    MatCheckboxModule,
    MatDialogModule,
    MatDividerModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatSidenavModule,
    MatToolbarModule,
    MatTabsModule,
    MatSelectModule,
    MatProgressBarModule
} from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { DynamicFormComponent } from './dynamic-form/dynamic-form.component';
import { DynamicFormQuestionComponent } from './dynamic-form-question/dynamic-form-question.component';
import { LoginComponent } from './login/login.component';
import { PrivacyComponent } from './privacy/privacy.component';
import { EventComponent } from './event/event.component';
import {MediaMatcher} from '@angular/cdk/layout';
import {ConfigService} from './config/config.service';
import { SidenavComponent } from './sidenav/sidenav.component';
import { ExportComponent } from './export/export.component';
import {TokenInterceptor} from './auth/token-interceptor.service';
import { AuthService} from './auth/auth.service';
import {AuthGuardService} from './auth/auth-guard.service';
import { ReportsComponent } from './reports/reports.component';
import {ReportsService} from './reports/reports.service';

@NgModule({
    declarations: [
        AppComponent,
        DynamicFormComponent,
        DynamicFormQuestionComponent,
        LoginComponent,
        PrivacyComponent,
        EventComponent,
        SidenavComponent,
        ExportComponent,
        ReportsComponent
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
        AppRoutingModule,
        MatFormFieldModule,
        MatInputModule,
        BrowserAnimationsModule,
        MatCardModule,
        MatCheckboxModule,
        MatDividerModule,
        MatButtonModule,
        MatDialogModule,
        MatIconModule,
        ReactiveFormsModule,
        MatToolbarModule,
        MatSidenavModule,
        MatListModule,
        MatTabsModule,
        MatSelectModule,
        MatProgressBarModule
    ],
    providers: [MediaMatcher,
        ConfigService,
        AuthService,
        AuthGuardService,
        ReportsService,
        {provide: HTTP_INTERCEPTORS,
        useClass: TokenInterceptor,
        multi: true
        }],
    bootstrap: [ AppComponent ]
})
export class AppModule { }
