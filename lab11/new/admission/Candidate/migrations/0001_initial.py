# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Candidate',
            fields=[
                ('id', models.AutoField(serialize=False, primary_key=True, verbose_name='ID', auto_created=True)),
                ('user_name', models.CharField(default='user', max_length=20)),
                ('password', models.CharField(default='user', max_length=15)),
                ('question', models.CharField(default='user', max_length=100)),
                ('answer', models.CharField(default='user', max_length=100)),
                ('roll_no', models.CharField(default='user', max_length=10)),
                ('category', models.CharField(default='user', max_length=3)),
                ('pdStatus', models.BooleanField(default=False)),
                ('preferences', models.CharField(default='', max_length=1000)),
                ('rankGE', models.CharField(default='0', max_length=5)),
                ('rankOBC', models.CharField(default='0', max_length=5)),
                ('rankSC', models.CharField(default='0', max_length=5)),
                ('rankST', models.CharField(default='0', max_length=5)),
                ('rankGEPD', models.CharField(default='0', max_length=5)),
                ('rankOBCPD', models.CharField(default='0', max_length=5)),
                ('rankSCPD', models.CharField(default='0', max_length=5)),
                ('rankSTPD', models.CharField(default='0', max_length=5)),
                ('final_seat', models.CharField(default='0', max_length=10)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='course',
            fields=[
                ('id', models.AutoField(serialize=False, primary_key=True, verbose_name='ID', auto_created=True)),
                ('code', models.CharField(default='', max_length=5)),
                ('go', models.CharField(default='', max_length=10)),
                ('gc', models.CharField(default='', max_length=10)),
                ('oo', models.CharField(default='', max_length=10)),
                ('oc', models.CharField(default='', max_length=10)),
                ('sco', models.CharField(default='', max_length=10)),
                ('scc', models.CharField(default='', max_length=10)),
                ('sto', models.CharField(default='', max_length=10)),
                ('stc', models.CharField(default='', max_length=10)),
                ('gpo', models.CharField(default='', max_length=10)),
                ('gpc', models.CharField(default='', max_length=10)),
                ('opo', models.CharField(default='', max_length=10)),
                ('opc', models.CharField(default='', max_length=10)),
                ('scpo', models.CharField(default='', max_length=10)),
                ('scpc', models.CharField(default='', max_length=10)),
                ('stpo', models.CharField(default='', max_length=10)),
                ('stpc', models.CharField(default='', max_length=10)),
                ('branch_name', models.CharField(default='', max_length=100)),
                ('college_name', models.CharField(default='', max_length=100)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
    ]
