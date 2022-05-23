FROM  registry.redhat.io/rhel8/python-38

ADD --chown=1001:0 app/* .
RUN pip install numpy 

CMD python main.py