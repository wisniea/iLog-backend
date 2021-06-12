package pl.wit.ilog.internals.web;

public interface IMapper<SRC,DST> {
    DST map(SRC e);
}